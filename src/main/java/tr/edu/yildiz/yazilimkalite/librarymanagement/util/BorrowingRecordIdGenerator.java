package tr.edu.yildiz.yazilimkalite.librarymanagement.util;

import java.io.Serializable;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class BorrowingRecordIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return RandomStringUtils.randomAlphanumeric(10);
    }
    
}
