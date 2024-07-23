package ua.natusvincere.echat.contact;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;
import ua.natusvincere.echat.exception.ForbiddenException;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contacts")
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
        if (principal == null) {
            throw new ForbiddenException("You are not authorized");
        }
        return ResponseEntity.ok(contactService.addContact(request, principal));
    }

    @GetMapping
    public ResponseEntity<List<ContactResponse>> findAll(Principal principal) {
        if (principal == null) {
            throw new ForbiddenException("You are not authorized");
        }
        return ResponseEntity.ok(contactService.findAll(principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(
            @PathVariable("id") UUID id,
            Principal principal
    ) {
        if (principal == null) {
            throw new ForbiddenException("You are not authorized");
        }
        contactService.deleteContact(id, principal);
        return ResponseEntity.noContent().build();
    }

}
