package api.doc.docman.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@IdClass(UserAuthority.class)
public class UserAuthority implements GrantedAuthority {
    @Id
    private String username;
    @Id
    private String authority;
}
