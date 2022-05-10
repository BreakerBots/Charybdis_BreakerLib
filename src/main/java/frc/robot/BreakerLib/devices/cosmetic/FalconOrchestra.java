// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices.cosmetic;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** If you're one of those damned souls that wants to make your robot speak. */
public class FalconOrchestra extends SubsystemBase {
    private Orchestra orchestra;
    private String[] currentPlaylist;
    private String loopedSong;
    private int nextPlaylistSong = 0;
    private boolean runPlaylist = false;
    private boolean runLooped = false;
    
    public FalconOrchestra() {
        orchestra = new Orchestra();
    }

    public void setOrchestraMotors(WPI_TalonFX... motors) {
        orchestra.clearInstruments();
        for (WPI_TalonFX motor: motors) {
            orchestra.addInstrument(motor);
        }
    }
    
    public void startPlaylist(String[] playlistSongFilepaths) {
        currentPlaylist = playlistSongFilepaths;
        runPlaylist = true;
    }

    public void startLoopedSong(String loopSongFilepath) {
        loopedSong = loopSongFilepath;
        runLooped = true;
    }
    public void stopPlaylist() {
        runPlaylist = false;
        nextPlaylistSong = 0;
    }

    public void stopLoopedSong() {
        runLooped = false;
    }

    public void stopMusic() {
        orchestra.stop();
    }

    public void pauseMusic() {
        orchestra.pause();
    }

    public void playMusic() {
        orchestra.play();
    }

    public void loadMusic(String musicFilepath) {
        orchestra.loadMusic(musicFilepath);
        stopPlaylist();
    }

    public boolean isPaused() {
        return !(orchestra.getCurrentTime() == 0) && !orchestra.isPlaying();
    }

    public boolean isStoped() {
        return (orchestra.getCurrentTime() == 0) && !orchestra.isPlaying();
    }

    public int getMusicTimestampMS() {
        return orchestra.getCurrentTime();
    }

    private void runPlaylistLoop() {
        if (runPlaylist && isStoped()) {
            try {
                orchestra.loadMusic(currentPlaylist[nextPlaylistSong]);
                orchestra.play();
                nextPlaylistSong ++;
            } catch (Exception e) {
                nextPlaylistSong = 0;
                orchestra.stop();
            }
        }
    }

    private void runLoopedSong() {
        if (runLooped && isStoped()) {
            orchestra.loadMusic(loopedSong);
            orchestra.play();
        }
    }

    @Override
    public void periodic() {
        runPlaylistLoop();
        runLoopedSong();
    }
}
