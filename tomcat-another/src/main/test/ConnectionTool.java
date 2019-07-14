import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionTool {
    static volatile CountDownLatch count = null; //用来在main方法中等待任务完成
    static AtomicInteger error = new AtomicInteger(0); //记载error的数量

    public static void testConnection(final URL url, int times) {
        count = new CountDownLatch(times); //初始化
        error = new AtomicInteger(0);

        final CountDownLatch latch = new CountDownLatch(1); //让Thread同时执行任务

        for (int i = 0; i < times; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await(); //等待一起执行
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(2000);
                        connection.getResponseCode();
                        connection.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                        error.getAndIncrement();
                    } finally {
                        count.countDown();
                    }
                }
            }).start();
        }
        latch.countDown();
    }
    public static void main(String[] args) throws MalformedURLException,
            InterruptedException {
        long time=0;
        long errors=0;
        for (int i = 0; i < 100; i++) {
            System.out.println("第"+i+"次");
            long begin = System.currentTimeMillis();
            // testConnection(new URL("http://localhost:8080/testConnection/index"),600);
            testConnection(new URL("http://localhost:6002/list"), 500);
            count.await();
            long end = System.currentTimeMillis();
            time+=(end-begin);
            errors+=error.get();
            System.out.println((end-begin)+"ms");
            System.out.println("错误个数:"+error.get());
            TimeUnit.SECONDS.sleep(5); //暂停5s
        }
        System.out.println(time /5.0 + "ms");
        System.out.println("error:" + errors / 5.0);
    }
}
