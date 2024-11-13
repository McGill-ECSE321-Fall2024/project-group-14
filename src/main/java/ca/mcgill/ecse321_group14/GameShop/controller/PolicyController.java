package ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321_group14.GameShop.dto.PolicyRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.PolicyResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Policy;
import ca.mcgill.ecse321_group14.GameShop.service.PolicyService;



@RestController
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    /**
     * Create a policy
     * 
     * @param policyRequestDto
     * @return PolicyResponseDto
     */
    @PostMapping("/policy")
    public PolicyResponseDto createPolicy(@RequestBody PolicyRequestDto policyRequestDto) {
        Policy policy = policyService.createPolicy(policyRequestDto.getDescription());
        return new PolicyResponseDto(policy);
    }

    /**
     * Get a policy by id
     * 
     * @param id
     * @return PolicyResponseDto
     */
    @GetMapping("/policy/{id}")
    public PolicyResponseDto getPolicy(@PathVariable int id) {
        Policy policy = policyService.getPolicy(id);
        return new PolicyResponseDto(policy);
    }

    /**
     * Update a policy by id
     * 
     * @param id
     * @param policyRequestDto
     * @return PolicyResponseDto
     */
    @PostMapping("/policy/update/{id}")
    public PolicyResponseDto updatePolicy(@PathVariable int id, @RequestBody PolicyRequestDto policyRequestDto) {
        Policy policy = policyService.updatePolicy(id, policyRequestDto.getDescription());
        return new PolicyResponseDto(policy);
    }

    /**
     * Delete a policy by id
     * 
     * @param id
     */
    @GetMapping("/policy/delete/{id}")
    public void deletePolicy(@PathVariable int id) {
        policyService.deletePolicy(id);
    }
}