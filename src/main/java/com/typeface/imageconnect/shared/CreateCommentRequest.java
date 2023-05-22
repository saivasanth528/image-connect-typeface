package com.typeface.imageconnect.shared;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentRequest {

    private Long imageId;
    private String username;
    private String commentText;

}

