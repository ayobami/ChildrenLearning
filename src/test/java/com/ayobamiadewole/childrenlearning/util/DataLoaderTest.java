/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayobamiadewole.childrenlearning.util;

import java.io.File;
import java.net.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
/**
 *
 * @author aa185298
 */
public class DataLoaderTest {
    
    private DataLoader dataLoader;   
    
    @Before
    public void setUp() {
        dataLoader= new DataLoader();
    }
    
    @After
    public void tearDown() {
        dataLoader=null;
    }

    @Test
     public void testGetEmotionVideos() 
     {
         String videoPath="C:\\EmoReact_V_1.0\\Data\\Test";
         List<EmotionVideo> emotionVideos=dataLoader.getEmotionVideos(videoPath);         
         assertNotNull(emotionVideos);
     }
     
     @Test
     public void testGetEmotionVideosURIs() 
     {
         String videoPath="C:\\EmoReact_V_1.0\\Data\\Test";
         List<URI> uRIs=dataLoader.getEmotionVideosURIs(videoPath);         
         assertNotNull(uRIs);
     }
     
      @Test
     public void testSplitLineToList() 
     {
         String label="1,2,3,4,5";
         List<String> labels=dataLoader.splitLineToList(label);         
         assertNotNull(labels);
     }
     
}

