/**
 *  ResourceCacheFile.java
 *  Adventure Game Interpreter Resource Package
 *
 *  Created by Dr. Z.
 *  Copyright (c) 2001 Dr. Z. All rights reserved.
 */

package com.sierra.agi.res;

import java.io.*;

public class ResourceCacheFile extends ResourceCache
{
    public ResourceCacheFile(File file) throws IOException, ResourceException
    {
        if (!file.exists())
        {
            throw new FileNotFoundException();
        }
        
        if (file.isDirectory())
        {
            loadFS(file);
        }
        
        if (resProvider == null)
        {
            String p = file.getPath();

            if (p.endsWith(".zip"))
            {
                //resProvider = new ResourceProviderZip(file);
            }
            else
            {
                loadFS(file);
            }
        }
    }

    protected void loadFS(File file) throws IOException, ResourceException
    {
        try
        {
            resProvider = new com.sierra.agi.res.v2.ResourceProviderV2(file);
        }
        catch (ResourceException e)
        {
            resProvider = new com.sierra.agi.res.v3.ResourceProviderV3(file);
        }
    }
}
