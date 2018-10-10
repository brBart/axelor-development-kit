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

  public void onBeforeSaveAny(@Observes @Priority(1) BeforeSave event) {
    System.out.println("BeforeSave");
  }

  public void onBeforeSaveContact(
      @Observes @Priority(2) @EntityType(Contact.class) BeforeSave event) {
    System.out.println("BeforeSave contact");
  }

  public void onBeforeSaveInvoice(
      @Observes @Priority(2) @EntityType(Invoice.class) BeforeSave event) {
    System.out.println("BeforeSave invoice");
  }

  @Inject private Event<BeforeSave> beforeSave;

  @Test
  public void test() {

    Contact contact = new Contact();
    Invoice invoice = new Invoice();

    System.out.println("fire contact");
    beforeSave.select(EntityTypes.get(Contact.class)).fire(new BeforeSave(contact));
    System.out.println();

    System.out.println("fire invoice");
    beforeSave.select(EntityTypes.get(Invoice.class)).fire(new BeforeSave(invoice));
    System.out.println();
  }
}
