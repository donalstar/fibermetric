//
//  DashboardViewController.h
//  fiber-counter
//
//  Created by Donal Carroll on 12/21/15.
//  Copyright © 2015 Donal Carroll. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DashboardViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>
{
    UITableView                 __weak *_tableView;
}

@property (nonatomic, weak) IBOutlet UITableView *tableView;

@end
