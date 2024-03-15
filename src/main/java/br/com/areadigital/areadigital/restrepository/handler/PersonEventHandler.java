package br.com.areadigital.areadigital.restrepository.handler;

import br.com.areadigital.areadigital.commons.Util;
import br.com.areadigital.areadigital.models.Person;
import br.com.areadigital.areadigital.restrepository.PersonRestRepository;
import br.com.areadigital.areadigital.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonEventHandler extends AbstractRepositoryEventListener<Person> {

    private final BCryptPasswordEncoder passwordEncoder;
    private final PersonRestRepository userRepository;


    /**
     * Override this method if you are interested in {@literal beforeCreate} events.
     *
     * @param entity The entity being created.
     */
    @Override
    protected void onBeforeCreate(Person entity) {
        try {
            if (entity.getPassword().length() > 0)
                entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        } catch (Exception e) {
            log.error("Password vazio");
            throw new ResourceNotFoundException("Empty Password");
        }
    }

    @Override
    protected void onBeforeSave(Person entity) {
        try {
            if (entity.getPassword().length() > 0)
                entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        } catch (Exception e) {
            log.error("Password vazio");
            throw new ResourceNotFoundException("Empty Password");
        } finally {
            Person s = userRepository.findById(entity.getId()).orElse(
                  new Person()
            );
            BeanUtils.copyProperties(entity, s, Util.getNullProperties(entity));
            BeanUtils.copyProperties(s, entity);
        }
    }
}
