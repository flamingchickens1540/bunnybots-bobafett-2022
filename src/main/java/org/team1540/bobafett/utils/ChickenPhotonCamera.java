package org.team1540.bobafett.utils;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public class ChickenPhotonCamera extends PhotonCamera {

    public ChickenPhotonCamera(String cameraName) {
        super(cameraName);
    }

    /** Finds and returns the data of an AprilTag with the specified fiducial ID
     * @param fiducialID the fiducial ID of the AprilTag to find
     * @return a {@link PhotonTrackedTarget} holding data of the AprilTag with the specified id if it is visible by the
     * camera, otherwise returns {@code null}
     */
    public PhotonTrackedTarget getTarget(int fiducialID) {
        PhotonPipelineResult result = getLatestResult();
        if (result.hasTargets()) {
            for (PhotonTrackedTarget target : result.getTargets()) {
                if (target.getFiducialId() == fiducialID) return target;
            }
        } return null;
    }
}
