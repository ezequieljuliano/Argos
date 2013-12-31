/*
 * Copyright 2013 Ezequiel Juliano Müller - ezequieljuliano@gmail.com.
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
package br.com.ezequieljuliano.argos.persistence;

import br.com.ezequieljuliano.argos.domain.Logger;
import br.com.ezequieljuliano.argos.domain.UserTerm;
import br.com.ezequieljuliano.argos.template.StandardDAO;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class LoggerDAO extends StandardDAO<Logger, String> {

    public boolean termsOfNotification(Logger logger) {
        if (!logger.getUser().getTerms().isEmpty()) {
            String searchString = "";
            for (UserTerm userTerm : logger.getUser().getTerms()) {
                searchString = searchString + userTerm.getTerm().getDescription() + ":" + userTerm.getValue() + " ";
            }
            Criteria criteria = Criteria.where("_id").is(logger.getId());
            List<Logger> loggers = findByFullText(Logger.COLLECTION_NAME, searchString, criteria, 100);
            return !loggers.isEmpty();
        }
        return false;
    }

}
