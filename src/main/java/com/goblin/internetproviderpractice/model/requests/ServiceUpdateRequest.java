package com.goblin.internetproviderpractice.model.requests;

import java.util.List;

public record ServiceUpdateRequest(String title, List<Integer> parameters) {
}
