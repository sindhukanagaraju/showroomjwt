package com.showroommanagement.service;

import com.showroommanagement.entity.Showroom;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.ShowroomRepository;
import com.showroommanagement.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowroomService {

    private final ShowroomRepository showroomRepository;

    public ShowroomService(final ShowroomRepository showroomRepository) {
        this.showroomRepository = showroomRepository;
    }

    @Transactional
    public Showroom createShowroom(final Showroom showroom) {
        return this.showroomRepository.save(showroom);
    }

    public Showroom retrieveShowroomById(final Integer id) {
        return this.showroomRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<Showroom> retrieveShowroom() {
        return this.showroomRepository.findAll();
    }

    public Showroom patchById(final Showroom showroom, final Integer id) {
        final Showroom existingShowroom = this.showroomRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (showroom.getName() != null) {
            existingShowroom.setName(showroom.getName());
        }
        return this.showroomRepository.save(existingShowroom);
    }

    @Transactional
    public Showroom updateShowroomById(final Showroom showroom, final Integer id) {
        final Showroom existingShowroom = this.showroomRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (showroom.getId() != null) {
            existingShowroom.setId(showroom.getId());
        }
        if (showroom.getName() != null) {
            existingShowroom.setName(showroom.getName());
        }
        if (showroom.getAddress() != null) {
            existingShowroom.setAddress(showroom.getAddress());
        }
        return this.showroomRepository.save(existingShowroom);
    }

    public String removeShowroomById(final Integer id) {
        final Showroom showroom = this.showroomRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        this.showroomRepository.delete(showroom);
        return Constant.REMOVE;
    }
}

