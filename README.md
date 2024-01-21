# Financius To (Realbyte) Money Manager

Just want to run the tool now? See here 👉 [Start here: migrating the data between apps](#start-here-migrating-the-data-between-apps)

---

Financius was a great tool to manage personal finances. Although completely unsupported/abandoned, it still works on my 
Android 12 phone. However, there are some limitations:

* Financius left Play Store
  * Application must be sideloaded to be installed on phone
  * From Android 14 onwards, sideloading applications will be more difficult (user needs to use ``adb`` to the APK file into device)
  * No more updates
* Application target API version is 21 (Android 5, Lollipop)
* Limited reporting capabilities
* No transaction searching

There are tons of personal finance apps either free or paid, but I choose Realbyte's Money Manager for these reasons:

* Flexible import and export functionality (if some day I decide to use another tool)
* Good transaction searching
* Good reporting capabilities (year, month, custom time period)
* Support for categories and subcategories (which may replace Financius tags according to the use case)
* Active app development
* Budgets functionality

You can find [Realbyte Money Manager on Google Play Store](https://play.google.com/store/apps/details?id=com.realbyteapps.moneymanagerfree).

This tool aims to migrate essential Financius data to Money Manager. It was created mainly to fit my personal 
use case, however I believe it may benefit the community in general with reasonable default values.

## Data that can be migrated

* Accounts
  * Account name
* Transactions
  * Transaction date (without time component)
  * Transaction type (Expense, Income, Transfer)
  * Transaction amount (raw amount without considering a currency)
  * Transaction notes
* Categories (in use by at least one Transaction)
  * Category name (migrated along with Transaction)
  * Association with transaction type (category for income or expense) through its usage on Transaction
* Tags (only first tag from each Transaction will be migrated as subcategory from main category)
  * Tag name

## Start here: migrating the data between apps

Here's a quick guide on how to get the necessary Financius export file and convert it to a file Money Manager can parse 
and import the data from.

### 1. Extract data from Financius

Create a Financius JSON backup file and then move it to the computer where you will run this tool

1. On Financius app, tap Hamburger/Side menu and then ``Settings``
2. Tap ``Your data``
3. Under ``Backup`` you should have a button ``Create backup``: tap it to start JSON backup creation
4. Select a destination folder on your phone and wait for the process to complete
5. After the backup file is generated, you need to move the file to your computer (eg. using a MicroSD card, USB cable,
   e-mail or cloud services)

### 2. Run data

Download this tool, rename the backup file to ``financius.json`` and place it on project root, then run the tool

1. Download this tool by either downloading the ZIP file and extracting it to a folder, or cloning the repository
2. Copy your Financius backup file to that folder, and rename it to ``financius.json``
3. On a terminal window, go to the folder you just downloaded and run:
   ```bash
   $ sh mvnw clean package # (or if you are on Windows run "mvnw.cmd" clean package instead
   $ java -jar target/financius-to-moneymanager.jar
   ```
4. If everything went well, you should now have a file ``exported.tsv`` with the contents that will be migrated to Money Manager. 
   You can also take time to give it a look to check if your data is according to your expectations (you can use a tool 
   like LibreOffice Calc or Microsoft Excel to make it easier - just be careful to not change/save the file)

### 3. Import data into Money Manager

Copy the result file ``exported.tsv`` to the phone where you have Money Manager installed and import the file into it. 
This step will require some manual work to validate the name of any categories and accounts that are being migrated from Financius,
but are not yet present on Money Manager.

**Attention:** if you already have data on Money Manager, I recommend you to create a backup file  at Money Manager 
in case you want to revert the changes by imported Financius data. My use case covered a fresh Money Manager installation (no previous data). 
I've not tested merging existing data from Money Manager and Financius, so proceed at your own risk if this is your use case.

1. Copy the ``exported.tsv`` file generated on previous step from your computer to your device (eg. using a MicroSD card, 
   USB cable, e-mail or cloud services). Store it on an easily accessible folder (eg: downloads folder)
2. Before migrating the data, check if your settings are appropriate. Open Money Manager, tap ``More`` and then ``Configuration``
   1. If you use Tags on Financius and you would like to preserve the first tag of each Transaction (see [limitations](#known-issues-and-limitations)),
      you need to enable ``Subcategory`` setting. By default its OFF; enable it if necessary. If not enabled and the import 
      file contains subcategories it will work as well, by ignoring the subcategories.
   2. Ensure your ``Main currency setting`` is correct. If not, change to the appropriate currency. **Attenttion:** changing 
      this setting will require data reinitialization, but if you need it chances are you don't have any data yet.
3. You are now read to start the migration. From main screen, tap ``More`` > ``Backup`` > ``Import excel file``. Click on 
  ``Import Excel file`` at bottom of screen and select the file your copied to your phone (``exported.tsv``). Once selected
  your return back to the import list, where the ``exported.tsv`` should now be available.
4. On the new screen, a list of every Transaction being migrated appears. At the top of the screen, you have a count of 
   found transactions to import. Your list may now contain some transactions with red text meaning a decision needs to be done. 
   These can be the following scenarios:
   1. ``'ABC' category does not exist.`` - the category you are trying to import does not exist yet in Money Manager. 
      Just tap it and select ``Add: ABC``. Category will be added as Expense or Income according to the Transaction type
   2. ``'XYZ' asset does not exist.`` - the same as above, but for Accounts. Just tap it and select ``Add: XYZ`` to create
      the Account (you don't need to specify the Account starting amount - see [limitations](#known-issues-and-limitations)). Although there are 
      multiple account types to choose from, I recommend sticking with ``Account``type because it displays the totals in 
      a simpler way (or if you already know how the other types work just select the type accordingly)
   3. ``'ABC -> JKL' category does not exist.`` - the subcategory does not exist. Similarly to categories, just tap ``Add: ABC -> JKL`` 
      to register it
5. Once you don't have any more Transactions with red text, tap the ``+`` (plus sign) at top of the page to save the Transactions.
   Repeat step 4. and 5. as necessary until no Transactions are left on the view.
6. Done! You should now have the transactions on Money Manager. Feel free to explore the application, namely to confirm your 
   transactions are there and also if account totals match on what you have on Financius. If everything match, then the migration 
   was successful. See the next section to understand some decisions taken regarding functionalities on Financius that are 
   not available on Money Manager.


## Known issues and limitations

* Accounts
    * Initial amount is migrated as an income transaction
    * Account visibility and description values are not migrated

* Transactions
    * Transaction time is not carried over to Money Manager (only year, month and day)
    * Money Manager does not seem to support transaction in a pending status, so these are migrated as normal/visible
      transactions. These kind of transactions will have "[Pending]" indication before its description on Money Manager
    * Transactions with "Exclude from reports" are migrated as normal/visible transactions (so they are included on Money
      Manager reports). These kind of transactions will have "[Excluded from reports]" indication before its description 
      on Money Manager

* Money Manager
    * The free version of the app is limited to 15 accounts

## Useful links

* [Money Manager input file specification](https://help.realbyteapps.com/hc/en-us/articles/360043223253-How-to-import-bulk-data-by-Excel-file)
* [Financius source code](https://github.com/mvarnagiris/financius) ([mirror](https://github.com/leplastic/financius))
