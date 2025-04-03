package com.showroommanagement.service;

import com.showroommanagement.entity.Branch;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.BranchRepository;
import com.showroommanagement.util.Constant;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(final BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Transactional
    public Branch createBranch(final Branch branch) {
        return this.branchRepository.save(branch);
    }

    public Branch retrieveBranchById(final Integer id) {
        return this.branchRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
    }

    public List<Branch> retrieveBranch() {
        return this.branchRepository.findAll();
    }

    @Transactional
    public Branch updateBranchById(final Branch branch, final Integer id) {
        final Branch existingBranch = this.branchRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        if (branch.getId() != null) {
            existingBranch.setId(branch.getId());
        }
        if (branch.getBranch() != null) {
            existingBranch.setBranch(branch.getBranch());
        }
        if (branch.getShowroom() != null) {
            existingBranch.setShowroom(branch.getShowroom());
        }
        return this.branchRepository.save(existingBranch);
    }

    public Branch removeBranchById(final Integer id) {
        final Branch branch = this.branchRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST));
        this.branchRepository.deleteById(id);
        return branch;
    }
}
