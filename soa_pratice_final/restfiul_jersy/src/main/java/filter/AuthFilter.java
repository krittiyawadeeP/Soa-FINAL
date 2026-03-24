package filter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import model.Customer;

@Provider
public class AuthFilter implements ContainerRequestFilter {
	@Override
	public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
		String authCredentials = containerRequest.getHeaderString(HttpHeaders.AUTHORIZATION);
		AuthService authsvc = new AuthService();
		boolean authStatus = authsvc.authenticate(authCredentials);
		if (!authStatus) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
	}
}



//
//public class AuthFilter implements ContainerRequestFilter {
//	@Override
//	public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
//		String authCredentials = containerRequest.getHeaderString(HttpHeaders.AUTHORIZATION);
//		String method = containerRequest.getMethod(); // ดึง HTTP Method (GET, POST, PUT, DELETE)
//
//		AuthService authsvc = new AuthService();
//		// ตรวจสอบ Username/Password และดึง Object Customer มา (ที่มีฟิลด์ userroles)
//		// [cite: 36, 37, 61, 62]
//		Customer user = authsvc.authenticate(authCredentials);
//
//		// 1. ถ้า Login ไม่ผ่าน (Username หรือ Password ผิด)
//		if (user == null) {
//			throw new WebApplicationException(
//					Response.status(Status.UNAUTHORIZED).entity("Invalid Credentials").build());
//		}
//
//		// 2. ตรวจสอบสิทธิ์ (Authorization)
//		// ถ้าเป็น Method ที่ส่งผลต่อข้อมูล (POST, PUT, DELETE)
//		if (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("DELETE")) {
//
//			// เช็คว่า User มี Role เป็น ADMIN หรือไม่ [cite: 6, 64, 66]
//			// หมายเหตุ: ใน DB ค่า USERROLES ถูกเก็บเป็น SET ('ADMIN', 'CLIENT') [cite: 6]
//			if (user.getUserroles() == null || !user.getUserroles().contains("ADMIN")) {
//				// ถ้าไม่ใช่ ADMIN แต่พยายามแก้ไข/ลบ ให้ส่ง 403 Forbidden
//				throw new WebApplicationException(Response.status(Status.FORBIDDEN)
//						.entity("Access Denied: Only ADMIN can perform this action.").build());
//			}
//		}
//
//		// ถ้าเป็น Method GET หรือเป็น ADMIN ระบบจะปล่อยให้ผ่านไปทำงานที่ Service ปกติ
//		// [cite: 20, 27]
//	}
//}


