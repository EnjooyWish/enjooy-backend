package az.enjooy.service.abstraction;

import az.enjooy.dto.ContactUsDTO;

public interface StaticsService {
    ContactUsDTO getContactUs();
    ContactUsDTO postContactUs(ContactUsDTO contactUsDTO);
}
