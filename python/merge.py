import sys
reload(sys)
sys.setdefaultencoding('iso-8859-1')

#@outputSchema("keywords:chararray")
def merge(line):
	if line is None:
		return line,'-1'
	print 'the line is'
	print line
	result=str(line)
	result = result.replace('(u\'','').replace('\',)','').replace('[','').replace(']','')
	keywords = result.split(',')
	string = ""
	for keyword in keywords:
		strings = keyword.split(':', 2)
		string += strings[0]
		string += ','
	return string[0:-1]

if __name__ == '__main__':
	line = "smart wifi:85,clothing shoes:97,e reader:862,crafts sewing:43"
	print merge(line)
