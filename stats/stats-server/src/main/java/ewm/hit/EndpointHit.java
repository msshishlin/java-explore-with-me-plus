package ewm.hit;

import jakarta.persistence.*;
import lombok.*;

import java.net.InetAddress;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private InetAddress ip;
    private LocalDateTime timestamp;
}
