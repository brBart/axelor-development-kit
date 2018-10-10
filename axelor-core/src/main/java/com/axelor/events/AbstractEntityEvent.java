/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2005-2018 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.events;

import com.axelor.db.EntityHelper;
import com.axelor.db.Model;
import com.axelor.rpc.Context;

public abstract class AbstractEntityEvent implements EntityEvent {

  private final Model entity;
  private Context context;

  public AbstractEntityEvent(Model entity) {
    this(entity, null);
  }

  public AbstractEntityEvent(Model entity, Context context) {
    this.entity = entity;
    this.context = context;
  }

  @Override
  public Model getEntity() {
    return entity;
  }

  @Override
  public Context getContext() {
    if (context == null) {
      context = new Context(entity.getId(), EntityHelper.getEntityClass(entity));
    }
    return context;
  }
}
