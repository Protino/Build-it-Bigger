package udacity.calgen.com.builditbigger;

import android.support.annotation.UiThread;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApiAsyncTest {

    @Test(timeout = 30*1000)
    @UiThread
    public void testEndpointAsyncTask() throws Exception {
        EndpointAsyncTask endpointAsyncTask = new EndpointAsyncTask();
        assertNotNull("Null response relieved, make sure local GAE server is running",
                endpointAsyncTask.execute().get());
    }
}