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
package com.axelor.event;

import com.axelor.db.Model;
import com.axelor.test.GuiceModules;
import com.axelor.test.GuiceRunner;
import com.axelor.test.db.Contact;
import com.axelor.test.db.Invoice;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GuiceRunner.class)
@GuiceModules({EventModule.class, TestModule.class})
public class TestEvents {

  public void onSaveAny(@Observes SaveEvent<?> event) {
    System.out.println("SaveEvent<?>");
  }

  public void onSaveModel(@Observes SaveEvent<? extends Model> event) {
    System.out.println("SaveEvent<? extends Model>");
  }

  public void onSaveContact(@Observes SaveEvent<Contact> event) {
    System.out.println("SaveEvent<Contact>");
  }

  public void onSaveInvoice(@Observes SaveEvent<Invoice> event) {
    System.out.println("SaveEvent<Invoice>");
  }

  @Inject private Event<SaveEvent<Contact>> contactEvent;
  @Inject private Event<SaveEvent<Invoice>> invoiceEvent;

  @Inject private Event<SaveEvent<? extends Model>> modelEvent;
  @Inject private Event<SaveEvent<?>> anyEvent;

  @Test
  public void test() {

    Contact contact = new Contact();
    Invoice invoice = new Invoice();

    System.out.println("fire contact");
    contactEvent.fire(new SaveEvent<>(contact));
    System.out.println();

    System.out.println("fire invoice");
    invoiceEvent.fire(new SaveEvent<>(invoice));
    System.out.println();

    System.out.println("fire ? extends model");
    modelEvent.fire(new SaveEvent<>(invoice));
    System.out.println();

    System.out.println("fire ?");
    anyEvent.fire(new SaveEvent<>(invoice));
    System.out.println();
  }
}
