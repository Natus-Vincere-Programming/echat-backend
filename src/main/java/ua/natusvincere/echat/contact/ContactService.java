package ua.natusvincere.echat.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.natusvincere.echat.exception.BadRequestException;
import ua.natusvincere.echat.exception.ForbiddenException;
import ua.natusvincere.echat.user.User;
import ua.natusvincere.echat.user.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactResponse addContact(AddContactRequest request, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ForbiddenException("User not found"));
        User contactUser = userRepository.findById(request.contactId())
                .orElseThrow(() -> new BadRequestException("Contact not found"));
        Contact contact = Contact.builder()
                .user(user)
                .contact(contactUser)
                .build();
        Contact savedContact = contactRepository.save(contact);
        return ContactResponse.builder()
                .id(savedContact.getId())
                .contactId(savedContact.getContact().getId())
                .build();
    }

    public List<ContactResponse> findAll(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ForbiddenException("User not found"));
        return contactRepository.findAllByUserId(user.getId())
                .stream()
                .map(contact -> ContactResponse.builder()
                        .id(contact.getId())
                        .contactId(contact.getId())
                        .build()
                )
                .toList();
    }

    public void deleteContact(UUID contactId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ForbiddenException("User not found"));
        contactRepository.deleteByUserIdAndContactId(user.getId(), contactId);
    }
}
