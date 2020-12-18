package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.LibrarySettingDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.EntityAlreadyExistsException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NonUpdatableFieldException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotExistingEntityException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.ValueNotCompatibleWithTypeException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.LibrarySetting;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.LibrarySettingType;
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
                throw new NonUpdatableFieldException("Name column of the LibrarySetting Entity cannot be changed.",
                        "name");
            }
            if (!settingDto.getType().equals(setting.getType())) {
                throw new NonUpdatableFieldException("Name column of the LibrarySetting Entity cannot be changed.",
                        "type");
            }

            checkValueTypeAndValue(settingDto.getType(), settingDto.getValue());
            setting.setValue(settingDto.getValue());
            setting.setType(settingDto.getType());

        } else {
            setting = new LibrarySetting();

            if(getByName(settingDto.getName()) != null) {
                throw new EntityAlreadyExistsException("Setting with specified name alreay exists.");
            }

            checkValueTypeAndValue(settingDto.getType(), settingDto.getValue());
            BeanUtils.copyProperties(settingDto, setting);
        }

        return librarySettingRepository.save(setting);
    }

    private LibrarySetting getByName(String name) {
        LibrarySetting setting = null;

        Optional<LibrarySetting> fetchedSetting = librarySettingRepository.findByName(name);

        if (fetchedSetting.isPresent()) {
            setting = fetchedSetting.get();
        }

        return setting;
    }

    public LibrarySetting getById(Long id) {
        LibrarySetting setting = null;

        Optional<LibrarySetting> fetchedSetting = librarySettingRepository.findById(id);

        if (fetchedSetting.isPresent()) {
            setting = fetchedSetting.get();
        }

        return setting;
    }

    private void checkValueTypeAndValue(LibrarySettingType type, String value) {
        if (type.equals(LibrarySettingType.NUMERIC)) {
            try {
                Long.parseLong(value);
            } catch (NumberFormatException e) {
                throw new ValueNotCompatibleWithTypeException("Only numeric values are accepted for this setting.");
            }
        }
    }

}
