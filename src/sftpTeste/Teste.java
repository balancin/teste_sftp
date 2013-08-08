package sftpTeste;

import java.util.ArrayList;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.PollingConsumer;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Teste {
	
	public static class FileProcessor implements  Processor {
		@Override
		public void process(Exchange exchange) throws Exception {
			System.out.println("from process");
			ArrayList<Exchange> grouped = exchange.getProperty(Exchange.GROUPED_EXCHANGE, ArrayList.class); 
			for(Exchange exch :grouped){
				System.out.println(exch.getIn().getBody(String.class));
				System.out.println(exch.getIn().getHeader(Exchange.FILE_NAME));
//				File file=exch.getIn().getBody(File.class);
//				System.out.println(exch.getProperty(Exchange.FILE_LOCAL_WORK_PATH));
//				//byte[]  filebytes=IOUtils.toByteArray(new FileInputStream(file));
//				System.out.println("name"+file.getName());
			}
		}
    }
	
	public static void main(String args[]){ 
		
		try {
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		RouteBuilder rb = new RouteBuilder() {
	        @Override
	        public void configure() throws Exception {
				from("file:/Users/Backup_fblsilva/Downloads/tmp?noop=true")
//				from("sftp://10.133.16.2//home/aut_asandro/cteste?username=aut_asandro&password=q1w2e3&idempotent=true&passiveMode=true&noop=true")
		        .convertBodyTo(String.class) 
		        .aggregate(constant(true)).completionSize(10).completionTimeout(10000L).groupExchanges() 
		        .process(new FileProcessor()); 
	        }
		};

	    CamelContext camelContext = null;
	    try {
	    	
		    camelContext = new DefaultCamelContext();
	        camelContext.addRoutes(rb);
	        camelContext.setTracing(true);
	        camelContext.start();
	        
	        Thread.sleep(3000);
	        // do other stuff...
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    finally {
	        try {
				camelContext.stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		
//		RouteBuilder rb = new RouteBuilder() {
//	        @Override
//	        public void configure() throws Exception {
////	        	from("sftp://10.133.16.2//home/aut_asandro?fileName=tokenization_2013-08-07.csv&delete=true&username=aut_asandro&password=q1w2e3&idempotent=true&passiveMode=true&noop=true")
////	        	.to("log:foo");
//	        	from("sftp://10.133.16.2//home/aut_asandro?username=aut_asandro&password=q1w2e3&idempotent=true&passiveMode=true&noop=true")
////	        	.onException(Exception.class).handled(true)
////	        	.to("log:errorLog?level=ERROR&showAll=true&multiline=true")
////	        	.setHeader("", constant("Error occured while transfer was in progress!")).end();
//	        	.to("log:foo");
//	        }
//	    };
//
//	    CamelContext camelContext = null;
//	    try {
//	    	
//		    camelContext = new DefaultCamelContext();
//	        camelContext.addRoutes(rb);
//	        camelContext.setTracing(true);
//	        camelContext.start();
//	        
//	        Thread.sleep(30000);
//	        // do other stuff...
//	    } catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    finally {
//	        try {
//				camelContext.stop();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    }
	}

}