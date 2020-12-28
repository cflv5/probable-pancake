package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.LibrarySettingDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.ValueNotCompatibleWithTypeException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.LibrarySetting;

@SpringBootTest
@ActiveProfiles("test")
public class LibrarySettingsServiceTest {
    @Autowired
    private LibrarySettingService librarySettingService;

    @Test
    public void getSettingByName_GivenSettingName() {
        String settingName = "maxExtension";

        LibrarySetting setting = librarySettingService.getByName(settingName);

        assertNotNull(setting);
    }

    @Test
    public void editSettingByName_GivenLibrarySettingDto() {
        String settingName = "maxExtension";

        LibrarySetting setting = librarySettingService.getByName(settingName);
        assertNotNull(setting);

        LibrarySettingDto settingDto = new LibrarySettingDto();
        settingDto.setId(setting.getId());
        settingDto.setName(setting.getName());
        settingDto.setType(setting.getType());
        settingDto.setValue("5");
        

        LibrarySetting editedSetting = librarySettingService.save(settingDto);
        assertNotNull(editedSetting);

        assertEquals("5", editedSetting.getValue());
    }

    @Test
    public void editSettingByName_GivenWrongTypeForValue_ThrowsValueNotCompatibleWithTypeException() {
        String settingName = "maxExtension";

        LibrarySetting setting = librarySettingService.getByName(settingName);
        assertNotNull(setting);

        LibrarySettingDto settingDto = new LibrarySettingDto();
        settingDto.setId(setting.getId());
        settingDto.setName(setting.getName());
        settingDto.setType(setting.getType());
        settingDto.setValue("TEXT");
        
        assertThrows(ValueNotCompatibleWithTypeException.class, () -> librarySettingService.save(settingDto));
    }
}
