package org.greengin.senseitweb.entities.rating;


import lombok.Getter;
import lombok.Setter;
import org.greengin.senseitweb.entities.AbstractEntity;
import org.greengin.senseitweb.entities.users.UserProfile;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;


@Entity
public class Comment extends AbstractEntity {

    @Basic
    @Getter
    @Setter
    Date date;

	@Basic
    @Getter
    @Setter
	String comment;

	@ManyToOne
    @Getter
    @Setter
	UserProfile user;

	@ManyToOne
    @Getter
    @Setter
	CommentThreadEntity target;

}
