package tmp;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EnvTest {
    @Test
    public void test(){
        Env env = new Env();
        assertThat(env.getValue(), is(true));
    }
}
