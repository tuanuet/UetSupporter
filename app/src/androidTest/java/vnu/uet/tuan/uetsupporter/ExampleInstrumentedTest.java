package vnu.uet.tuan.uetsupporter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation fragment_feedback_detailthongbao, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under fragment_feedback_detailthongbao.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("vnu.uet.tuan.uetsupporter", appContext.getPackageName());
    }
}
