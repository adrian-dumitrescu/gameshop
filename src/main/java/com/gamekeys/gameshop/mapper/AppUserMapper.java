package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.AppUserDto;
import com.gamekeys.gameshop.dto.basic.ActivationKeyBasicDto;
import com.gamekeys.gameshop.entity.ActivationKey;
import com.gamekeys.gameshop.entity.AppUser;
import com.gamekeys.gameshop.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserMapper implements Mapper<AppUser, AppUserDto> {

    private AppUserRepository appUserRepository;

    @Override
    public AppUserDto convertToDto(AppUser entity) {
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setId(entity.getId());
        appUserDto.setFirstName(entity.getFirstName());
        appUserDto.setLastName(entity.getLastName());
        appUserDto.setEmail(entity.getEmail());
        appUserDto.setPassword(entity.getPassword());
        appUserDto.setRoles(entity.getRoles());
        appUserDto.setProfileImageUrl(entity.getProfileImageUrl());
        appUserDto.setJoinDate(entity.getJoinDate());
        appUserDto.setIsNotLocked(entity.getIsNotLocked());
        appUserDto.setIsEnabled(entity.getIsEnabled());
        appUserDto.setNickname(entity.getNickname());
        appUserDto.setCountry(entity.getCountry());
        appUserDto.setGender(entity.getGender());
        appUserDto.setAge(entity.getAge());
        appUserDto.setActivationKey(entity.getActivationKeys().stream().map(activationKey -> activationKeyToBasicDto(activationKey)).collect(Collectors.toSet()));
        return appUserDto;
    }


    @Override
    public AppUser convertToEntity(AppUserDto dto) {
        AppUser appUser = new AppUser();
        appUser.setId(dto.getId());
        appUser.setFirstName(dto.getFirstName());
        appUser.setLastName(dto.getLastName());
        appUser.setEmail(dto.getEmail());
        appUser.setPassword(dto.getPassword());
        appUser.setRoles(dto.getRoles());
        appUser.setProfileImageUrl(dto.getProfileImageUrl());
        appUser.setJoinDate(dto.getJoinDate());
        appUser.setIsEnabled(dto.getIsNotLocked());
        appUser.setIsEnabled(dto.getIsEnabled());
        appUser.setNickname(dto.getNickname());
        appUser.setCountry(dto.getCountry());
        appUser.setGender(dto.getGender());
        appUser.setAge(dto.getAge());
        if(dto.getActivationKey() != null) {
            appUser.setActivationKeys(appUserRepository.getReferenceById(dto.getId()).getActivationKeys());
        }
        return appUser;
    }

    private ActivationKeyBasicDto activationKeyToBasicDto(ActivationKey entity) {
        ActivationKeyBasicDto result = new ActivationKeyBasicDto();
        result.setId(entity.getId());
        result.setKey(entity.getKeyValue());
        return result;
    }

}
