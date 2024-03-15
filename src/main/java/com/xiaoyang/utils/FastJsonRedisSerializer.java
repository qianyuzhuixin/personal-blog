package com.xiaoyang.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * Redis使用FastJson序列化
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {


    //定义了一个静态常量DEFAULT_CHARSET，表示默认的字符编码方式为UTF-8。
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");


    private Class<T> clazz;


    //定义了一个静态代码块，它设置了全局的ParserConfig，使其自动支持类型推断。
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    // 构造函数，接收一个泛型参数clazz，表示要序列化的对象的类型。
    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    // 实现RedisSerializer接口的serialize方法，将对象序列化为字节数组。
    //重写了serialize方法，用于将Java对象序列化为JSON字符串。
    // 首先检查输入对象是否为null，如果是，则返回一个长度为0的字节数组。
    // 否则，使用JSON.toJSONString方法将对象转换为JSON字符串，并将其转换为字节数组。
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }


    // 重写了deserialize方法，用于将JSON字符串反序列化为Java对象。
    // 首先检查输入的字节数组是否为null或长度为0，如果是，则返回null。
    // 否则，使用new String(bytes, DEFAULT_CHARSET)方法将字节数组转换为字符串。
    // 最后，使用JSON.parseObject方法将字符串解析为Java对象，并将对象的类信息设置为之前存储的clazz。
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return JSON.parseObject(str, clazz);
    }


    //定义了一个名为getJavaType的方法，用于根据类信息创建一个JavaType对象。
    // 这个方法主要用于类型推断，但它在这里没有使用到。
    //  你可以根据需要自行实现这个方法。
    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
