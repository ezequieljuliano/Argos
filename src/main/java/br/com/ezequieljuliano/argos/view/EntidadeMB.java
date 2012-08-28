/*
 * Copyright 2012 Ezequiel Juliano Müller.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.ezequieljuliano.argos.view;

import br.com.ezequieljuliano.argos.business.EntidadeBC;
import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.util.JsfUtils;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Parameter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@ViewController
public class EntidadeMB {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private EntidadeBC bc;
    
    @Inject
    private MessageContext messageContext;
    
    @Inject
    private Parameter<Integer> id;
    
    private Entidade bean;

    public Entidade getBean() {
        if (bean == null) {
            bean = new Entidade();
            if (this.id.getValue() != null) {
                this.bean = bc.load(Long.valueOf(this.id.getValue()));
            }
        }
        return bean;
    }

    public void setBean(Entidade bean) {
        this.bean = bean;
    }

    @Transactional
    public void salvar() {
        try {
            bc.saveOrUpdate(bean);
            messageContext.add("Registro salvo com sucesso!", SeverityType.INFO);
        } catch (Exception e) {
            messageContext.add("Ocorreu um erro ao salvar o registro!", SeverityType.ERROR);
        }
    }

    @Transactional
    public void inativar() {
        try {
            bc.inativar(bean);
            messageContext.add("Registro inativado com sucesso!", SeverityType.INFO);
        } catch (Exception e) {
            messageContext.add("Ocorreu um erro ao inativar o registro!", SeverityType.ERROR);
        }
    }

    @Transactional
    public void ativar() {
        try {
            bc.ativar(bean);
            messageContext.add("Registro ativado com sucesso!", SeverityType.INFO);
        } catch (Exception e) {
            messageContext.add("Ocorreu um erro ao ativar o registro!", SeverityType.ERROR);
        }
    }

    public List<Entidade> getList() {
        return this.bc.findAll();
    }

    public void handleSelect(SelectEvent e) {
        try {
            JsfUtils.redireciona("entidade_edit.jsf?faces-redirect=true&id=" + ((Entidade) e.getObject()).getId());
        } catch (Exception ex) {
            Logger.getLogger(EntidadeMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addEntidadeFilha(SelectEvent e) {
        try {
            EntidadeBC bcEntFilha = new EntidadeBC();
            Entidade entFilha = bcEntFilha.load(((Entidade) e.getObject()).getId());
            bc.addEntidadeFilha(bean, entFilha);
            messageContext.add("Entidade vinculada com sucesso!", SeverityType.INFO);
        } catch (Exception ex) {
            messageContext.add("Ocorreu um erro ao vincular a entidade!", SeverityType.ERROR);
        }
    }
}