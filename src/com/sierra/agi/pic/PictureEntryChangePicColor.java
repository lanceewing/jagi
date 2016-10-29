/*
 *  PictureEntryChangePicColor.java
 *  Adventure Game Interpreter Picture Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.pic;

import com.sierra.agi.awt.*;

public class PictureEntryChangePicColor extends PictureEntry
{
    protected byte picColor;

    public PictureEntryChangePicColor(byte picColor)
    {
        this.picColor = picColor;
    }

    public void draw(PictureContext pictureContext)
    {
        pictureContext.picColor = pictureContext.translatePixel(picColor);
    }
}
