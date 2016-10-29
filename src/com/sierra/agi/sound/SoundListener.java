/*
 *  SoundListener.java
 *  Adventure Game Interpreter Sound Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.sound;

/**
 * Interface used by <CODE>Sound</CODE> derivated classes
 * to be notified of specific events.
 *
 * @see     com.sierra.agi.sound.Sound
 * @author  Dr. Z
 * @version 0.00.00.01
 */
public interface SoundListener extends java.util.EventListener
{
    /** The sound has finished */
    public static final byte STOP_REASON_FINISHED = (byte)0;

    /** The sound has been stopped programmatically */
    public static final byte STOP_REASON_PROGRAMMATICALLY = (byte)1;

    /** The sound has been stopped by an exception */
    public static final byte STOP_REASON_EXCEPTION = (byte)2;

    /** Called when the <CODE>Sound</CODE> starts playing. */
    public void soundStarted(SoundClip sound);

    /** Called when the <CODE>Sound</CODE> has stopped. */
    public void soundStopped(SoundClip sound, byte reason);

    /** Called when the <CODE>Sound</CODE> volume has been modified. */
    public void soundVolumeChanged(SoundClip sound, int volume);
}