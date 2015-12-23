//
//  DetailViewController.h
//  fibermetric
//
//  Created by Donal Carroll on 12/22/15.
//  Copyright © 2015 Donal Carroll. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DetailViewController : UIViewController

@property (strong, nonatomic) id detailItem;
@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;

@end

