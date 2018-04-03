package net.sf.javaanpr.test;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import org.hamcrest.CoreMatchers.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

@RunWith(Parameterized.class)
public class RecognitionAllIt {
    private static final Logger logger = LoggerFactory.getLogger(RecognitionIT.class);

    private String inputFile;
    private String expectedPlate;

    @Parameterized.Parameters
    public static Collection makeParameters() throws IOException {
        //String snapshotDirPath = "src/test/resources/snapshots";
        String resultsPath = "src/test/resources/results.properties";
        InputStream resultsStream = new FileInputStream(new File(resultsPath));

        Properties properties = new Properties();
        properties.load(resultsStream);
        resultsStream.close();
        assertThat(properties.size() > 0, is(equalTo(true)));
        //assertTrue(properties.size() > 0);
        Collection bob = new ArrayList();
        properties.forEach((key,value)->{
            bob.add(new Object[] {key,value});

        });
        //File snapshotDir = new File(snapshotDirPath);
        //File[] snapshots = snapshotDir.listFiles();
        //assertNotNull(snapshots);



        return bob;
    }
    public RecognitionAllIt(String inputFile,String expectedPlate){
        this.inputFile = inputFile;
        this.expectedPlate = expectedPlate;
    }

    @Test
    public void intelligenceParameterized() throws IOException, ParserConfigurationException, SAXException {

        logger.info("####### Running: "+inputFile+" ######");
        final String image = "snapshots/"+inputFile;
        CarSnapshot carSnap = new CarSnapshot(image);
        assertThat(carSnap,is(notNullValue()));
        assertThat(carSnap.getImage(),is(notNullValue()));
        //assertNotNull("carSnap is null", carSnap);
        //assertNotNull("carSnap.image is null", carSnap.getImage());
        Intelligence intel = new Intelligence();
        assertThat(intel,is(notNullValue()));
        //assertNotNull(intel);
        String spz = intel.recognize(carSnap);
        assertThat(spz,is(notNullValue()));
        //assertNotNull("The licence plate is null - are you sure the image has the correct color space?", spz);
        assertThat(expectedPlate,is(equalTo(spz)));
        //assertEquals(expectedPlate, spz);
        carSnap.close();
    }

}
