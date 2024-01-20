# Financius To (Realbyte) MoneyManager

Financius was a great tool to manage personal finances. Although completely unsupported/abandoned, it still works on my 
Android 12 phone. However, there are some limitations:

* Financius left Play Store
  * Application must be sideloaded to be installed on phone
  * From Android 14 onwards, sideloading applications will be more difficult
  * No more updates
* Application target API version is 21 (Android 5, Lollipop)
* Limited reporting capabilities
* No transaction searching

There are tons of personal finance apps either free or paid, but I choose Realbyte's Money Manager for these reasons:

* Flexible import and export functionality
* Good transaction searching
* Good reporting capabilities (year, month, custom time period)
* Support for categories and subcategories (which may replace Financius tags according to the use case)
* Active app development
* Budgets
* Export into multiple formats if some day I decide to use another tool

You can find [Realbyte MoneyManager on Google Play Store](https://play.google.com/store/apps/details?id=com.realbyteapps.moneymanagerfree).

This application aims to migrate essential Financius data to Money Manager. It was created mainly to fit my personal 
use case, however I believe the tool may benefit the community in general with reasonable default values.

## Data that can be migrated by this tool

* Accounts
  * Account name
* Transactions
  * Transaction date (not time)
  * Transaction type (Expense, Income, Transfer)
  * Transaction amount (raw amount without considering a currency)
  * Transaction notes
* Categories (as main category)
  * Category name (migrated along with Transaction)
  * Association between transaction type (income/expense) through Transaction own type
* Tags (only first tag from each Transaction will be migrated as subcategory from main category)
  * Tag name

## Start here: migrating the data between apps

Here's a quick guide on how to get the necessary Financius export file and convert it to a file Money Manager can parse 
and import the data from.

### 1. Extract data from Financius

Create a Financius JSON backup file and then move it to the computer where you will run this tool

1. Go to Hamburger/Side menu and tap ``Settings``
2. Go to ``Your data``
3. Under ``Backup`` you should have a button ``Create backup``. Tap it to start JSON backup creation
4. Select a destination folder on your phone and wait for the process to complete
5. After the backups is done, you need somehow to move the file to your computer (eg. using a MicroSD card, USB cable,
   e-mail or cloud services)

### 2. Run data

Download this tool, rename the backup file to ``financius.json`` and place it on project root, then run the tool

1. TODO

....

### 3. Import data into Money Manager

Copy the result file ``exported.tsv`` to the phone where you have Money Manager installed and import the file into it. 
This step will require some manual work to validate the name of any categories and accounts that are being migrated from Financius,
but are not yet present on Money Manager.

**Attention:** if you already have data on Money Manager, I recommend you to Money Manager backup capabilities to create a backup file 
in case you want to revert changes by imported Financius data. My use case covered a fresh Money Manager installation (no previous data). 
I've not tested merging existing data from Money Manager and Financius, so proceed at your own risk if this is your use case.

1. TODO

....



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

## Useful links

* [Money Manager input file specification](https://help.realbyteapps.com/hc/en-us/articles/360043223253-How-to-import-bulk-data-by-Excel-file)
* [Financius source code](https://github.com/mvarnagiris/financius) ([mirror](https://github.com/leplastic/financius))
