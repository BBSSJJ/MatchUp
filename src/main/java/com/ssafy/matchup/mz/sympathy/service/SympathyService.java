package com.ssafy.matchup.mz.sympathy.service;

import com.ssafy.matchup.mz.sympathy.dto.request.UpdateSympathyRequestDto;

public interface SympathyService {
    void updateSympathy(Long userId, Long articleId, UpdateSympathyRequestDto updateSympathyRequestDto);
}
