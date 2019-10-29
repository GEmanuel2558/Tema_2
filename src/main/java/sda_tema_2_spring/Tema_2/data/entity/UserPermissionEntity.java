package sda_tema_2_spring.Tema_2.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users_permissions", schema = "sda_proiect_comun1")
public class UserPermissionEntity {

    @Id
    @Column(name = "id", insertable = false, updatable = false, unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "permissions_id", nullable = false, insertable = false, updatable = false)
    private Integer permissionsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permissions_id", referencedColumnName = "id")
    private PermissionEntity bindUsersPermissionsEntities;

    public UserPermissionEntity() {
    }

    public UserPermissionEntity(Integer userId, Integer permissionsId) {
        this.userId = userId;
        this.permissionsId = permissionsId;
    }

    public UserPermissionEntity(Integer userId, PermissionEntity bindUsersPermissionsEntities) {
        this.userId = userId;
        this.bindUsersPermissionsEntities = bindUsersPermissionsEntities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(Integer permissionsId) {
        this.permissionsId = permissionsId;
    }

    public PermissionEntity getBindUsersPermissionsEntities() {
        return bindUsersPermissionsEntities;
    }

    public void setBindUsersPermissionsEntities(PermissionEntity bindUsersPermissionsEntities) {
        this.bindUsersPermissionsEntities = bindUsersPermissionsEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPermissionEntity that = (UserPermissionEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(permissionsId, that.permissionsId) &&
                Objects.equals(bindUsersPermissionsEntities, that.bindUsersPermissionsEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, permissionsId, bindUsersPermissionsEntities);
    }

    @Override
    public String toString() {
        return "UserPermissionEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", permissionsId=" + permissionsId +
                '}';
    }
}
