"""
split and interactively page a string or file of text
"""

def more(text, numlines=15):
	lines = text.splitlines()		# like split('\n') but no '' at end
	while lines:
		chunk = lines[:numlines]
		lines = lines[numlines:]
		for line in chunk: print(line)
More?y
	if lines and input('More?') not in ['y', 'Y']: break

if __name__ == '__main__':
	import sys
	more(open(sys.argv[1]).read(), 10)
