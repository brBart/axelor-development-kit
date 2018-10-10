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
package com.axelor.events.qualifiers;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public final class EntityTypes {

  @SuppressWarnings("all")
  private static final class EntityTypeImpl implements EntityType {

    private Class<?> value;

    public EntityTypeImpl(Class<?> value) {
      this.value = value;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
      return EntityType.class;
    }

    @Override
    public Class<?> value() {
      return this.value;
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof EntityType && ((EntityType) obj).value().isAssignableFrom(value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(31, value);
    }
  }

  private static final LoadingCache<Class<?>, EntityType> CACHE =
      CacheBuilder.newBuilder()
          .weakKeys()
          .build(
              new CacheLoader<Class<?>, EntityType>() {
                public EntityType load(Class<?> key) throws Exception {
                  return new EntityTypeImpl(key);
                }
              });

  private EntityTypes() {}

  public static EntityType get(Class<?> type) {
    try {
      return CACHE.get(type);
    } catch (ExecutionException e) {
      throw new IllegalArgumentException(
          "cannot initialize EntityType annotation for: " + type.getName());
    }
  }
}
