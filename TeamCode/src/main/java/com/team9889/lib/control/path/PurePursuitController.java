package com.team9889.lib.control.path;

import com.team9889.lib.control.math.Pose;
import com.team9889.lib.control.math.Rotation2d;
import com.team9889.lib.control.math.Vector2d;
import com.team9889.lib.control.path.PoseSequence;

public class PurePursuitController {
    private Vector2d position, velocity, acceleration;
    private double r, maxSteering, maxSpeed;
    private double radius;
    private Vector2d startPosition;

    private PoseSequence sequence;

    public PurePursuitController(PoseSequence sequence, double radius, Vector2d startPosition, double maxSpeed, double maxSteering){
        this.sequence = sequence;
        this.radius = radius;
        this.startPosition = startPosition;
        this.maxSpeed = maxSpeed;
        this.maxSteering = maxSteering;
        acceleration = new Vector2d(0,0);
        velocity = new Vector2d(maxSpeed, 0);
    }

    public static void main(String... args){
        PoseSequence poseSequence = new PoseSequence();

        double width = 2048.0;
        double height = 1024.0;
//        poseSequence.addPose(0, height/2);
//        poseSequence.addPose(new Pose(width/4, (height/2)+100);
//        poseSequence.addPose(new Pose(width/2, height/2);
        poseSequence.addPose(new Pose(new Vector2d(), new Rotation2d()));

//        PurePursuitController controller = new PurePursuitController()
    }

    public void update() {
        Vector2d predict = velocity.copy();
        predict.normalize();
        predict.set(predict.multiple(25));
        Vector2d predictpos = Vector2d.add(position, predict);

        // Now we must find the normal to the path from the predicted position
        // We look at the normal for each line segment and pick out the closest one

        Vector2d normal = null;
        Vector2d target = null;
        double worldRecord = 1000000;  // Start with a very high record distance that can easily be beaten

        // Loop through all points of the path
        for (int i = 0; i < sequence.numberOfPoses() - 1; i++) {

            // Look at a line segment
            Vector2d a = sequence.poses.get(i).getVector2D();
            Vector2d b = sequence.poses.get(i + 1).getVector2D();

            // Get the normal point to that line
            Vector2d normalPoint = getNormalPoint(predictpos, a, b);
            // This only works because we know our path goes from left to right
            // We could have a more sophisticated test to tell if the point is in the line segment or not
            if (normalPoint.getX() < a.getX() || normalPoint.getX() > b.getX()) {
                // This is something of a hacky solution, but if it's not within the line segment
                // consider the normal to just be the end of the line segment (point b)
                normalPoint = b.copy();
            }

            // How far away are we from the path?
            double distance = Vector2d.distance(predictpos, normalPoint);
            // Did we beat the record and find the closest line segment?
            if (distance < worldRecord) {
                worldRecord = distance;
                // If so the target we want to steer towards is the normal
                normal = normalPoint;

                // Look at the direction of the line segment so we can seek a little bit ahead of the normal
                Vector2d dir = Vector2d.subtract(b, a);
                dir.normalize();
                // This is an oversimplification
                // Should be based on distance to path & velocity
                dir.set(dir.multiple(10));
                target = normalPoint.copy();
                target.add(dir);
            }
        }
    }

    // A function to get the normal point from a point (p) to a line segment (a-b)
    // This function could be optimized to make fewer new Vector objects
    public Vector2d getNormalPoint(Vector2d p, Vector2d a, Vector2d b) {
        // Vector from a to p
        Vector2d ap = Vector2d.subtract(p, a);
        // Vector from a to b
        Vector2d ab = Vector2d.subtract(b, a);
        ab.normalize(); // Normalize the line
        // Project vector "diff" onto line by using the dot product
        ab.multiple(ap.dot(ab));
        Vector2d normalPoint = Vector2d.add(a, ab);
        return normalPoint;
    }

    // Method to update position
    public void updateStuff() {
        // Update velocity
        velocity.add(acceleration);
        // Limit speed
        velocity.limit(maxSpeed);
        position.add(velocity);
        // Reset accelertion to 0 each cycle
        acceleration.set(acceleration.multiple(0));
    }

    void applyForce(Vector2d force) {
        // We could add mass here if we want A = F / M
        acceleration.set(acceleration.add(force));
    }


    // A method that calculates and applies a steering force towards a target
    // STEER = DESIRED MINUS VELOCITY
    void seek(Vector2d target) {
        Vector2d desired = Vector2d.subtract(target, position);  // A vector pointing from the position to the target

        // If the magnitude of desired equals 0, skip out of here
        // (We could optimize this to check if x and y are 0 to avoid mag() square root
        if (desired.mag() == 0) return;

        // Normalize desired and scale to maximum speed
        desired.normalize();
        desired.set(desired.multiple(maxSpeed));
        // Steering = Desired minus Velocity
        Vector2d steer = Vector2d.subtract(desired, velocity);
        steer.limit(maxSteering);  // Limit to maximum steering force

        applyForce(steer);
    }
}
