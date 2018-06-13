insert into category (CategoryName) values('Outlook Problems');
insert into category (CategoryName) values('PC Smoking');
insert into region (RegionName) values('North East');
insert into region (RegionName) values('South East');
insert into region (RegionName) values('Central');


insert into location(locationname,regionId) values('Little Rock',1);
insert into location(locationname,regionId) values('Heber Springs',3);
insert into location(locationname,regionId) values('Malvern',5);
insert into location(locationname,regionId) values('Beebe',3);
insert into location(locationname,regionId) values('Searcy',2);
insert into location(locationname,regionId) values('RusselVille',3);
insert into location(locationname,regionId) values('Silom Springs',2);
insert into location(locationname,regionId) values('Fayetteville',4);
insert into location(locationname,regionId) values('Eldorado',1);
insert into location(locationname,regionId) values('Texarkana',5);
insert into location(locationname,regionId) values('Blyville',5);
insert into location(locationname,regionId) values('Waldo',2);
insert into location(locationname,regionId) values('Flippin',7);
insert into location(locationname,regionId) values('Madison',2);
insert into location(locationname,regionId) values('Thornton',1);
insert into location(locationname,regionId) values('Oak Grove',2);


insert into siteadmin(siteadminname,emailaddress,phonenumber,locationId,siterole)
values('Bob Smith','you@me.com',123456789,4,'User');
insert into siteadmin(siteadminname,emailaddress,phonenumber,locationId,siterole)
values('Manny Smith','you@me.com',123456789,9,'User');
insert into siteadmin(siteadminname,emailaddress,phonenumber,locationId,siterole)
values('Susan Smith','you@me.com',123456789,5,'User');
insert into siteadmin(siteadminname,emailaddress,phonenumber,locationId,siterole)
values('Mary Smith','you@me.com',123456789,8,'User');
insert into siteadmin(siteadminname,emailaddress,phonenumber,locationId,siterole)
values('Louis Smith','you@me.com',123456789,1,'Admin');
insert into siteadmin(siteadminname,emailaddress,phonenumber,locationId,siterole)
values('Barbara Smith','you@me.com',123456789,2,'Admin');
insert into siteadmin(siteadminname,emailaddress,phonenumber,locationId,siterole)
values('Default Administrator','you@me.com',123456789,2,'Admin');


insert into TicketStatus (statusName) values('Pending CS Reply');
insert into TicketStatus (statusName) values('New Ticket');
insert into TicketStatus (statusName) values('Waiting on Parts');
insert into TicketStatus (statusName) values('On Schedule');
insert into TicketStatus (statusName) values('Resolved');
insert into TicketStatus (statusName) values('Pending Customer Reply');


insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Joe Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',1,5,4,2,8);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Joe Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',4,3,2,5,6);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Joe Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',2,2,3,5,4);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Joe Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',7,1,3,4,8);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Joe Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',7,2,2,4,6);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('John Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',7,2,5,4,8);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Mary Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',7,2,5,4,8);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Luke Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',1,2,4,4,8);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Timothy Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',1,2,3,4,7);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Joe Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',2,2,3,4,3);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Joe Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',2,2,3,4,3);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Louis Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',2,2,3,4,6);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Alma Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',2,5,3,4,7);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Shaneka Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',1,4,3,4,8);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Nirvana Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',1,4,3,1,4);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Cody Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',1,2,3,2,8);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Chance Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',1,2,3,5,1);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Baxter Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',1,3,3,3,2);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Melissa Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',2,3,5,6,2);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Kristin Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',2,3,4,2,2);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Josiah Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',3,2,4,4,2);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Wolfgang Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',3,5,4,2,4);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Geoff Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',3,2,4,4,4);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Jeff Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',3,2,4,5,5);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Greg Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',4,2,4,5,5);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Savannah Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',4,5,3,5,5);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Kavanna Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',5,3,3,4,5);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Crystal Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',5,2,3,6,5);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Krystal Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',5,2,2,6,5);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Amber Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',5,2,5,4,5);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Joe Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',6,2,5,2,5);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Josie Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',8,4,1,2,5);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Jose Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',8,4,1,2,6);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Maggie Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',8,2,1,2,6);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Lisa Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',8,2,2,3,7);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Bart Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',8,2,2,3,7);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Homer Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',10,2,2,3,7);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Jacob Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',10,2,2,5,7);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Barney Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',10,1,2,5,7);

insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Michael Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',10,2,3,4,7);


insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Velma Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',12,1,3,4,7);


insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('Craig Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',11,2,3,4,8);


insert into ticket (Name,PhoneNumber,EmailAddress,AssetTagNumber
SubjectTitle,Description,ComputerName,LocationId,PriorityId,CategoryId,
StatusId,SiteAdminId)
values ('John Smith', 123456789,'you@me.co',210210210,
'Need Help','Microsoft Outlook Problems','wert210210210',13,1,4,5,1);