package ua.natusvincere.echat.contact;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactResponse> addContact(
            @RequestBody AddContactRequest request,
            Principal principal
    ) {
        return ResponseEntity.ok(contactService.addContact(request, principal));
    }

    @GetMapping
    public ResponseEntity<List<ContactResponse>> findAll(Principal principal) {
        return ResponseEntity.ok(contactService.findAll(principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(
            @PathVariable("id") UUID id,
            Principal principal
    ) {
        contactService.deleteContact(id, principal);
        return ResponseEntity.noContent().build();
    }

}
