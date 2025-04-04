package ewm.hit;

import ewm.CreateEndpointHitDto;
import ewm.EndpointHitDto;
import ewm.exception.UnknownIpException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Mapper
public interface EndpointHitMapper {
    EndpointHitMapper INSTANCE = Mappers.getMapper(EndpointHitMapper.class);


    @Mapping(source = "ip", target = "ip", qualifiedByName = "ipToString")
    @Mapping(source = "timestamp", target = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EndpointHitDto toEndpointHitDto(EndpointHit endpointHit);

    @Mapping(source = "ip", target = "ip", qualifiedByName = "stringToIp")
    @Mapping(source = "timestamp", target = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EndpointHit toEndpointHit(CreateEndpointHitDto createEndpointHitDto);

    @Named("ipToString")
    static String ipToString(InetAddress ip) {
        return ip.getHostAddress();
    }

    @Named("stringToIp")
    static InetAddress stringToIp(String ip) {
        try {
            return InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            throw new UnknownIpException("Ошибка в заполнении ip адреса");
        }
    }
}
