package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.LibrarySettingDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NonUpdatableFieldException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotExistingEntityException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.LibrarySetting;
import tr.edu.yildiz.yazilimkalite.librarymanagement.repository.LibrarySettingRepository;

@Service
public class LibrarySettingService {
    @Autowired
    private LibrarySettingRepository librarySettingRepository;

    public List<LibrarySetting> getAll() {
        List<LibrarySetting> settings = new ArrayList<>();
        librarySettingRepository.findAll().forEach(settings::add);
        return settings;
    }

    public LibrarySetting save(LibrarySettingDto settingDto) {
        LibrarySetting setting = null;

        if (settingDto.getId() != null) {
            setting = getById(settingDto.getId());

            if (setting == null) {
                throw new NotExistingEntityException(
                        "Library Setting with id " + settingDto.getId() + " does not exist.");
            }

            if (!settingDto.getName().equals(setting.getName())) {
                throw new NonUpdatableFieldException("Name column of the LibrarySetting Entity cannot be changed.");
            }

            setting.setValue(settingDto.getValue());
            setting.setType(settingDto.getType());

        } else {
            setting = new LibrarySetting();
            BeanUtils.copyProperties(settingDto, setting);
        }

        return librarySettingRepository.save(setting);

    }

    public LibrarySetting getById(Long id) {
        LibrarySetting setting = null;

        Optional<LibrarySetting> fetchedSetting = librarySettingRepository.findById(id);

        if (fetchedSetting.isPresent()) {
            setting = fetchedSetting.get();
        }

        return setting;
    }

}
