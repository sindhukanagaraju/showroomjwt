package com.showroommanagement.service;

import com.showroommanagement.entity.Branch;
import com.showroommanagement.entity.Showroom;
import com.showroommanagement.exception.BadRequestServiceAlertException;
import com.showroommanagement.repository.BranchRepository;
import com.showroommanagement.util.Constant;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchService branchService;

    private Branch branch;

    @BeforeAll
    public static void toStartBranchService() {
        System.out.println("Branch Service Test case execution has been Started");
    }

    @BeforeEach
    void setUp() {
        branch = new Branch();
        branch.setId(1);
        branch.setBranch("chennai");
    }

    @Test
    void testCreateBranch() {
        when(branchRepository.save(any(Branch.class))).thenReturn(branch);

        Branch savedBranch = branchService.createBranch(branch);

        assertNotNull(savedBranch);
        assertEquals(1, savedBranch.getId());
        assertEquals("chennai", savedBranch.getBranch());
        verify(branchRepository, times(1)).save(any(Branch.class));
    }

    @Test
    void testRetrieveBranchById() {
        when(branchRepository.findById(1)).thenReturn(Optional.of(branch));

        Branch foundBranch = branchService.retrieveBranchById(1);

        assertNotNull(foundBranch);
        assertEquals(branch.getId(), foundBranch.getId());
        assertEquals(branch.getBranch(), foundBranch.getBranch());
        verify(branchRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveBranchByIdNotFound() {
        when(branchRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> branchService.retrieveBranchById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(branchRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveAllBranches() {
        when(branchRepository.findAll()).thenReturn(List.of(branch));

        List<Branch> branches = branchService.retrieveBranch();

        assertNotNull(branches);
        assertEquals(1, branches.size());
        assertEquals(branch.getId(), branches.get(0).getId());
        verify(branchRepository, times(1)).findAll();
    }

    @Test
    void testUpdateBranchById() {
        Showroom updatedShowroom = new Showroom();
        updatedShowroom.setName("Poorvika Updated");
        updatedShowroom.setAddress("1/2 Sipcot Information Technology Park, Near Siruseri Special Economic Zone Navallur Post, Sirucheri, chennai.");
        updatedShowroom.setContactNumber("9056734576");

        Branch updatedBranch = new Branch();
        updatedBranch.setBranch("updatedChennai");
        updatedBranch.setShowroom(updatedShowroom);

        when(branchRepository.findById(1)).thenReturn(Optional.of(branch));
        when(branchRepository.save(any(Branch.class))).thenReturn(updatedBranch);

        Branch result = branchService.updateBranchById(updatedBranch, 1);

        assertNotNull(result);
        assertEquals("updatedChennai", result.getBranch());
        assertEquals("Poorvika Updated", result.getShowroom().getName());
        verify(branchRepository, times(1)).findById(1);
        verify(branchRepository, times(1)).save(any(Branch.class));
    }

    @Test
    void testUpdateBranchByIdNotFound() {
        Showroom  updatedShowroom = new Showroom();
        updatedShowroom.setName("Poorvika Updated");
        updatedShowroom.setAddress("1/2 Sipcot Information Technology Park, Near Siruseri Special Economic Zone Navallur Post, Sirucheri, chennai.");
        updatedShowroom.setContactNumber("9056734576");

        Branch updatedBranch = new Branch();
        updatedBranch.setBranch("updatedChennai");
        updatedBranch.setShowroom(updatedShowroom);

        when(branchRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> branchService.updateBranchById(updatedBranch, 1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(branchRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteBranchById() {
        when(branchRepository.findById(1)).thenReturn(Optional.of(branch));

        String result = branchService.removeBranchById(1);

        assertEquals(Constant.REMOVE, result);
        verify(branchRepository, times(1)).findById(1);
        verify(branchRepository, times(1)).delete(any(Branch.class));
    }

    @Test
    void testDeleteBranchByIdNotFound() {
        when(branchRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestServiceAlertException.class, () -> branchService.removeBranchById(1));

        assertEquals(Constant.ID_DOES_NOT_EXIST, exception.getMessage());
        verify(branchRepository, times(1)).findById(1);
    }

    @AfterAll
    public static void toEndBranchService() {
        System.out.println("Branch Service Test case has been execution finished");
    }
}
