Test Cases for A Priori Rule Generation - Spring 2014

file			min support	min confidence	notes
transactions1.txt	.25		.5
			.5		.66
			.75		.66
			.5		.8
			0		0
			-1.0		0		one error
			0		-1.0		one error
			-1.0		-1.0		two errors
			1.1		0		one error
			0		1.1		one error
			1.1		1.1		two errors
transactions2.txt	0		0		bad format
transactions3.txt	0		0		bad format
transactions4.txt	0		0		bad format
transactions5.txt	.25		.5
transactions6.txt	0.014		0.2		1000 transactions w/ 100 items
transactions7.txt	0.012		0.6		10000 transactions w/ 1000 items