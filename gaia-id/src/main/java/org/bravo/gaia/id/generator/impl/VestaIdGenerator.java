package org.bravo.gaia.id.generator.impl;

import org.bravo.gaia.id.config.IdProperties;
import org.bravo.gaia.id.generator.IdGenerator;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Preconditions;
import com.robert.vesta.service.factory.IdServiceFactoryBean;
import com.robert.vesta.service.intf.IdService;

public class VestaIdGenerator implements IdGenerator<Long>, InitializingBean {

    private IdService idService;

    private IdProperties.Vesta vesta;

    public VestaIdGenerator(IdProperties.Vesta vesta) {
        this.vesta = vesta;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        IdServiceFactoryBean idServiceFactoryBean = new IdServiceFactoryBean();
        idServiceFactoryBean.setMachineId(vesta.getMachine());
        idServiceFactoryBean.setType(vesta.getType());
        idServiceFactoryBean.setGenMethod(vesta.getGenMethod());
        idServiceFactoryBean.setProviderType(IdServiceFactoryBean.Type.PROPERTY);
        idServiceFactoryBean.init();

        this.idService = idServiceFactoryBean.getObject();

        Preconditions.checkArgument(idService != null, "idService is null");
    }

    @Override
    public Long generate() {
        return this.idService.genId();
    }
}