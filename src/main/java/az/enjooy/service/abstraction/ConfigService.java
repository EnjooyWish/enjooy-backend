package az.enjooy.service.abstraction;

import az.enjooy.dto.ConfigurationPostDTO;
import az.enjooy.dto.ResponseDTO;

public interface ConfigService {
    String getValueByName(String name);
    ResponseDTO findAll();
    ResponseDTO findById(Long id);
    ResponseDTO findByName(String name);
    ResponseDTO getAllByType(String type);
    ResponseDTO save(ConfigurationPostDTO configurationPostDTO);
}
