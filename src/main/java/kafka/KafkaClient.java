package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaClient {
    public static void main(String[] args) {


        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "spidercdh-01:9092");
        /**
         * acks=0 客户端不会等待服务端的确认; acks=1 只会等待leader分区的确认; acks=all或者acks=-1
         * 等待leader分区和follower分区的确认
         *
         */
        props.put("acks", "all");
        /**
         * 设置大于0的值将使客户端重新发送任何数据，一旦这些数据发送失败。
         * 注意，这些重试与客户端接收到发送错误时的重试没有什么不同。允许重试将潜在的改变数据的顺序，如果这两个消息记录都是发送到同一个partition，
         * 则第一个消息失败第二个发送成功，则第二条消息会比第一条消息出现要早。
         */
        props.put("retries", 0);
        /**
         * producer将试图批处理消息记录，以减少请求次数。这将改善client与server之间的性能。这项配置控制默认的批量处理消息字节数。
         * 不会试图处理大于这个字节数的消息字节数。 发送到brokers的请求将包含多个批量处理，其中会包含对每个partition的一个请求。
         * 较小的批量处理数值比较少用，并且可能降低吞吐量（0则会仅用批量处理）。较大的批量处理数值将会浪费更多内存空间，这样就需要分配特定批量处理数值的内存大小。
         */
        props.put("batch.size", 16384);
        /**
         * producer组将会汇总任何在请求与发送之间到达的消息记录一个单独批量的请求。通常来说，
         * 这只有在记录产生速度大于发送速度的时候才能发生。然而，在某些条件下，
         * 客户端将希望降低请求的数量，甚至降低到中等负载一下。这项设置将通过增加小的延迟来完成--即，不是立即发送一条记录，
         * producer将会等待给定的延迟时间以允许其他消息记录发送，这些消息记录可以批量处理。这可以认为是TCP种Nagle的算法类似。
         * 这项设置设定了批量处理的更高的延迟边界：一旦我们获得某个partition的batch.size，他将会立即发送而不顾这项设置，
         * 然而如果我们获得消息字节数比这项设置要小的多，我们需要“linger”特定的时间以获取更多的消息。
         * 这个设置默认为0，即没有延迟。设定linger.ms=5，例如，将会减少请求数目，但是同时会增加5ms的延迟。
         */
        props.put("linger.ms", 1);

        /**
         * producer可以用来缓存数据的内存大小。如果数据产生速度大于向broker发送的速度，producer会阻塞或者抛出异常，以“block.on.buffer.full”来表明。
         * 这项设置将和producer能够使用的总内存相关，但并不是一个硬性的限制，因为不是producer使用的所有内存都是用于缓存。
         * 一些额外的内存会用于压缩（如果引入压缩机制），同样还有一些用于维护请求。
         */
        props.put("buffer.memory", 33554432);

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            // send()方法是异步的
//            producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
            producer.send(new ProducerRecord<String, String>("test", 0, "", Integer.toString(i)));

            producer.close();
        }
    }
}
